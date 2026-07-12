package com.mylibrary.libraryapp.entities;

import com.mylibrary.libraryapp.entities.Enum.BookStatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "borrowing_books")
public class BorrowsBookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "borrow_id", nullable = false)
    private BorrowsEntity borrows;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private BookEntity book;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookStatusEnum status;

    private Date returnDate;

    private String reason;

}
