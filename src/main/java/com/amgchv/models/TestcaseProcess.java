package com.amgchv.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"testcase_testcase_id", "user_user_id"})
})
public class TestcaseProcess {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long testcaseProcessId;

    @OneToOne
    @NonNull
    private User user;

    @OneToOne
    @NonNull
    private Testcase testcase;

    @NonNull
    private LocalDateTime startDate;

    private LocalDateTime endDate;
}
