package com.example.demo.repository.Recording;

import com.example.demo.entity.recording.RecordingUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecordingUserRepository extends JpaRepository<RecordingUser, Long> {
    Optional<RecordingUser> findByName(String name);
}
