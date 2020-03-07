package com.speedmail;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailSendApiKeyRepository extends JpaRepository<EmailSendApiKeyEntity,String> {

}
