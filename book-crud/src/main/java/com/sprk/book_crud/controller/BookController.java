package com.sprk.book_crud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sprk.book_crud.entity.Book;
import com.sprk.book_crud.service.BookService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/show-form")
    public String showForm(Model model, HttpSession session){

        session.setAttribute("bookId", 0);

        model.addAttribute("book", new Book());

        return "book-form";
    }
    
    @PostMapping("/process-form")
    public String insertBook(@Valid @ModelAttribute Book book,BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpSession session){

        if(bindingResult.hasErrors()){
            return "book-form";
        }else{
            Book userBook = new Book();
            int bookId = (int) session.getAttribute("bookId");

            if(bookId != 0){
                book.setBookId(bookId);
                userBook.setBookId(bookId);
            }else{
                book.setBookId(0);
            }

            boolean isInserted = bookService.saveBook(book);

            if(isInserted){

                if(userBook.getBookId()!=0){
                    redirectAttributes.addFlashAttribute("msg", "Updated Succesfully..");
                }else{
                    redirectAttributes.addFlashAttribute("msg", "Inserted Succesfully..");
                }
                
            }

            return "redirect:/book/show-dashboard";
        }   
    }

    @GetMapping("/show-dashboard")
    public String showDashboard(Model model){

        List<Book> books = bookService.getAllBooks();
        
        model.addAttribute("books", books);

        return "dashboard";
    }

    @GetMapping("/update/{bookId}")
    public String showUpdateForm(@PathVariable int bookId, Model model, RedirectAttributes redirectAttributes, HttpSession session){
        Book book = bookService.getBookById(bookId);

        if(book == null){
            redirectAttributes.addFlashAttribute("msg","Book Id = "+ bookId +" not found...");
            return "redirect:/book/show-dashboard";
        }else{
            model.addAttribute("book", book);
            session.setAttribute("bookId", bookId);
            return "book-form";
        }
    }

    @GetMapping("/delete/{bookId}")
    public String deleteBook(@PathVariable int bookId,RedirectAttributes redirectAttributes){
        Book book = bookService.getBookById(bookId);

        if(book == null){
            redirectAttributes.addFlashAttribute("msg","Book Id = " + bookId + " doesn't exists!");
            return "redirect:/book/show-dashboard";
        }else{
            bookService.deleteBookById(book);
            redirectAttributes.addFlashAttribute("msg","Book Deleted successfully....");
            return "redirect:/book/show-dashboard";
        }
    }
}
