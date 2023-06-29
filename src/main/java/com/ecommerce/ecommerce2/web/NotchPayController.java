package com.ecommerce.ecommerce2.web;

import com.ecommerce.ecommerce2.Entity.NotificationData;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class NotchPayController {





    @PostMapping("/callback")
    public ResponseEntity<Void> handlePaymentCallback(@RequestBody NotificationData notificationData) {
        // Traiter les données de notification reçues de NotchPay
        String paymentStatus = notificationData.getStatus();
        String paymentReference = notificationData.getReference();
        // Effectuer les actions nécessaires en fonction des données de notification

        // Répondre à la notification avec un statut OK (200)
        return ResponseEntity.ok().build();
    }

    @PostMapping("/cancel")
    public ResponseEntity<Void> CancelPayment(@RequestBody NotificationData notificationData) {
        String paymentStatus = notificationData.getStatus();
        if (paymentStatus.equals("cancelled")) {
            // Effectuer la redirection vers la page souhaitée après l'annulation
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header("Location", "https://example.com/cancelled-page")
                    .build();
        } else {
            // Traiter les autres cas de statut de paiement si nécessaire
            return ResponseEntity.ok().build();
        }
    }

}
