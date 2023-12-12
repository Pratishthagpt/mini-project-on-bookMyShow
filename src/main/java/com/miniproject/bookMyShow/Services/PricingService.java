package com.miniproject.bookMyShow.Services;

import com.miniproject.bookMyShow.Models.Show;
import com.miniproject.bookMyShow.Models.ShowSeat;
import com.miniproject.bookMyShow.Models.ShowSeatType;
import com.miniproject.bookMyShow.Repositories.ShowSeatTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PricingService {
    private ShowSeatTypeRepository showSeatTypeRepository;

    @Autowired
    public PricingService(ShowSeatTypeRepository showSeatTypeRepository) {
        this.showSeatTypeRepository = showSeatTypeRepository;
    }

    public int getPrice (List<ShowSeat> showSeats, Show show) {
        /**
         * 1. Get list of all the showSeatTypes present for a show
         * 2. Match the seat type with the showSeats (that user selected) and if it matches then add the price
         *    for that showSeatType
         */
        int price = 0;
        List<ShowSeatType> showSeatTypes = showSeatTypeRepository.findAllByShow(show);
        for(ShowSeat showSeat : showSeats) {
            for (ShowSeatType showSeatType : showSeatTypes) {
                if(showSeat.getSeat().getSeatType().equals(showSeatType.getSeatType())) {
                    price += showSeatType.getPrice();
                    break;
                }
            }
        }
        return price;
    }
}
