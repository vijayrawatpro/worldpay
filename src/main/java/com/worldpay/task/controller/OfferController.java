package com.worldpay.task.controller;

import com.worldpay.task.domain.Offer;
import com.worldpay.task.dto.AddOfferDto;
import com.worldpay.task.exception.DoesNotExistException;
import com.worldpay.task.exception.InvalidDateException;
import com.worldpay.task.exception.OfferExpiredException;
import com.worldpay.task.service.OfferService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1/offers")
public class OfferController extends BaseController {

    @Autowired
    private OfferService offerService;

    @GetMapping(value = "/{merchantId}", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Offer>> offers(@PathVariable String merchantId) {
        return ok(offerService.getOffers(merchantId));
    }

    @PostMapping(value = "/{merchantId}", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Offer> addOffer(@PathVariable String merchantId, @RequestBody AddOfferDto addOfferDto)
        throws InvalidDateException {
        return created(offerService.addOffer(merchantId, addOfferDto));
    }

    @PatchMapping(value = "/{offerId}/cancel", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Offer> cancelOffer(@PathVariable String offerId)
        throws OfferExpiredException, DoesNotExistException {
        return ok(offerService.cancelOffer(offerId));
    }
}
