package com.speedmail;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailSendSourceControlRepository extends JpaRepository<EmailSendSourceControlEntity,String> {

}
