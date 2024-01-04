package com.biblio.xpress.controller;

import com.biblio.xpress.dto.BookDTO;
import com.biblio.xpress.entity.*;
import com.biblio.xpress.repository.CategoryRepository;
import com.biblio.xpress.service.*;
import com.biblio.xpress.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;


@Controller

public class BookController {
    private final BookService bookService;
    private final CategoryRepository categoryRepository;
    private final UserServiceImpl userService;
    private final ReservationService reservationService;
    private final BorrowService borrowService;
    private final NotificationService notificationService;
    private final CardService cardService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    public BookController(BookService bookService, CategoryRepository categoryRepository, UserServiceImpl userService, ReservationService reservationService, BorrowService borrowService, NotificationService notificationService, CardService cardService) {
        this.bookService = bookService;
        this.categoryRepository = categoryRepository;
        this.userService = userService;
        this.reservationService = reservationService;
        this.borrowService = borrowService;
        this.notificationService = notificationService;
        this.cardService = cardService;
    }
    //Add Book View
    @GetMapping("/add-book")
    public String showAddBookForm(Model model) {
        model.addAttribute("bookDTO", new BookDTO());
        return "add-book";
    }
    //Get All Books
    @GetMapping("/books/")
    public String showAllBooks(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);

        return "books";
    }
    //Get My Borrowed Books
    @GetMapping("/borrowed/")
    public ResponseEntity<List<Borrow>> showMyBorrowedBooks(Authentication authentication) {
        Optional<UserEntity> user = userService.findUserByEmail(authentication.getName());

        if (user.isPresent()) {
            List<Borrow> borrowedBooks = borrowService.getMyBorrowedBooks(user.get());
            return ResponseEntity.ok(borrowedBooks);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    //Get My Borrowed Books View
    @GetMapping("/borrowedBooks")
    public String showMyBooks() {
        return "borrowed";
    }
    //Reserve A book
    @PostMapping(value="/books/reserve/{id}")
    public ResponseEntity<Reservation> ReserveBook(@PathVariable Long id, Authentication authentication, Principal principal) {
        Optional<UserEntity> user = userService.findUserByEmail(authentication.getName());
        Optional<Book> book = bookService.getBookById(id);
        Card card = cardService.getMyCard(user.get());
        Reservation reservation = new Reservation();
        reservation.setBook(book.get());
        reservation.setUser(user.get());
        reservation.setAvailable(false);
        if (card.isValid()) {
            if (reservationService.addReservation(reservation) != null) {
                return ResponseEntity.ok(reservation);
            }
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return null;
    }
    //Search for books by title or category
    @GetMapping("/search")
    public String searchBooks(
            Model model,
            @RequestParam(name = "term", required = false) String terms,
            @RequestParam(name = "category", required = false) Long categoryId) {
        List<Book> searchResults = bookService.searchBooks(terms, categoryId);

        model.addAttribute("searchResults", searchResults);

        return "searchResults";
    }
    //Borrow a book request
    @PostMapping(value="/books/borrow/{id}")
    public ResponseEntity<List<Borrow>> BorrowBook(@PathVariable Long id,Authentication authentication, Principal principal) {
        Optional<UserEntity> user = userService.findUserByEmail(authentication.getName());
        Card card = cardService.getMyCard(user.get());
        System.out.println(card.isValid());
        if (card.isValid()) {
            Optional<Book> book = bookService.getBookById(id);
            if (book.get().getNumberOfCopies()!=0) {
                book.get().setNumberOfCopies(book.get().getNumberOfCopies() - 1);
                bookService.saveBook(book.get());
                Borrow borrow = new Borrow();
                borrow.setBook(book.get());
                borrow.setUser(user.get());
                borrow.setBorrowDate(Date.valueOf(LocalDate.now()));
                borrow.setReturnDate(Date.valueOf(LocalDate.now().plusDays(7)));
                borrow.setReturned(false);
                borrowService.newBorrow(borrow);
                return ResponseEntity.ok(borrowService.getMyBorrowedBooks(user.get()));
            }
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        }
        return null;
    }
    //Return a book
    @PostMapping(value="/books/unborrow/{BookId}/{borrowId}")
    public ResponseEntity<Book> unBorrowBook(@PathVariable Long BookId, @PathVariable Long borrowId, Authentication authentication, Principal principal) {
        Optional<UserEntity> user = userService.findUserByEmail(authentication.getName());
        Optional<Book> book = bookService.getBookById(BookId);
        book.get().setNumberOfCopies(book.get().getNumberOfCopies()+1);
        bookService.saveBook(book.get());
        Borrow borrow = borrowService.getBorrowedBookById(borrowId);
        borrow.setReturned(true);
        borrow.setReturnedDate(Date.valueOf(LocalDate.now()));
        if (borrow.getReturnDate() != null && LocalDate.now().isAfter(borrow.getReturnDate().toLocalDate())) {
            borrow.setOverdue(true);
        }
        if (borrowService.newBorrow(borrow)!=null){
            Reservation reservation = reservationService.availableReservation(book,0);
            if (reservation!=null) {
                reservation.setAvailable(true);
                reservation.setNotificationCount(1);
                reservationService.addReservation(reservation);
                notificationService.notifyUserBookAvailable(reservation);
            }
            return ResponseEntity.ok(borrow.getBook());
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    //Add a new book and notify users that subscribed to a specific category
    @PostMapping(value="/add-new-book", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createBook(@RequestBody @Valid BookDTO bookDTO, BindingResult result, RedirectAttributes attributes) {

        Book book = new Book();
        book.setIsbn(bookDTO.getIsbn());
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        Category category = categoryRepository.getById(bookDTO.getCategoryId());
        book.setCategory(category);
        book.setNumberOfCopies(bookDTO.getNumberOfCopies());
        java.util.Date utilDate = bookDTO.getPublicationDate();
        Date sqlDate = new Date(utilDate.getTime());
        book.setPublicationDate(sqlDate);

        Book savedBook = bookService.saveBook(book);

        Long bookId = savedBook.getId();
        if (savedBook!=null) {
            attributes.addAttribute("id", bookId);
            List<UserEntity> usersTobeNotified = userService.findAll();
            Iterator<UserEntity> it = usersTobeNotified.iterator();
            while (it.hasNext()) {
                UserEntity user = it.next();
                Iterator<Category> favCategory = user.getFavoriteCategories().iterator();
                while (favCategory.hasNext()) {
                    Category userCategory = favCategory.next();
                    System.out.println(userCategory.getName().equals(category.getName()));
                    if (userCategory.getName().equals(category.getName())) {
                        String notificationMessage = "The book " + book.getTitle().toUpperCase() + " in the category " + category.getName() + " is now available.";
                        messagingTemplate.convertAndSendToUser(user.getUsername(), "/topic/notification", notificationMessage);
                    }
                }

            }
            return ResponseEntity.ok(savedBook.toString());
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

}
