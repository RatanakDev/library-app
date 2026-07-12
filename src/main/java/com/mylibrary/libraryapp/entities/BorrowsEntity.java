package com.mylibrary.libraryapp.entities;

import com.mylibrary.libraryapp.entities.Enum.RequestStatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "borrowing")
public class BorrowsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
    private Date borrowDate;
    private Date returnDate;

    @OneToMany(mappedBy = "borrows", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BorrowsBookEntity> borrowsBooks = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private RequestStatusEnum requestStatus = RequestStatusEnum.PENDING;

}
