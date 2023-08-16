package de.gessnerfl.fakesmtp.smtp.server;

import de.gessnerfl.fakesmtp.config.FakeSmtpConfigurationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlockedRecipientAddresses {
    private static final Logger LOGGER = LoggerFactory.getLogger(BlockedRecipientAddresses.class);

    final List<String> whiteListedAddresses;
    final List<String> blockedAddresses;

    @Autowired
    public BlockedRecipientAddresses(FakeSmtpConfigurationProperties fakeSmtpConfigurationProperties) {
        this.blockedAddresses = fakeSmtpConfigurationProperties.getBlockedRecipientAddresses()
                .stream()
                .map(String::toLowerCase)
                .toList();

        this.whiteListedAddresses = fakeSmtpConfigurationProperties.getWhiteListedRecipientAddresses()
                .stream()
                .map(String::toLowerCase)
                .toList();
    }

    public boolean isBlocked(String recipient){
        if (recipient == null) {
            LOGGER.info("Message blocked due to missing recipient");
            return true;
        }
        if (blockedAddresses.contains(recipient.toLowerCase())) {
            LOGGER.info("Message blocked due to recipient {} found in blocked addresses", recipient);
            return true;
        }
        if (whiteListedAddresses.isEmpty()) {
            return false;
        }
        if (whiteListedAddresses.stream().noneMatch(a -> recipient.toLowerCase().endsWith(a))) {
            LOGGER.info("Message blocked due to recipient {} not found in white listed addresses", recipient);
            return true;
        }
        return false;
    }
}
