package com.yunseojin.MyLittleHomepage.domain.feedback.command.repository;

import com.yunseojin.MyLittleHomepage.domain.feedback.command.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository<T extends Feedback> extends JpaRepository<T, Long> {

}