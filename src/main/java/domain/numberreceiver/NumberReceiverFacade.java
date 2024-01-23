package domain.numberreceiver;


import domain.numberreceiver.dto.InputNumberResultDto;
import domain.numberreceiver.dto.TicketDto;
import lombok.AllArgsConstructor;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

import static domain.numberreceiver.dto.InputNumberResultDto.*;

@AllArgsConstructor
public class NumberReceiverFacade {

    private final NumberValidator numberValidator;
    private final NumberReceiverRepository repository;
    private final Clock clock;

    public InputNumberResultDto inputNumbers(Set<Integer> numbersFromUser) {
        boolean filteredNumbers = numberValidator.filterAllNumbersInOfRange(numbersFromUser);
        if(filteredNumbers) {
            String ticketId = UUID.randomUUID().toString();
            LocalDateTime drawDate = LocalDateTime.now(clock);
            Ticket savedTicket = repository.save(new Ticket(ticketId, drawDate, numbersFromUser));
            return builder()
                    .drawDate(savedTicket.drawDate())
                    .ticketId(savedTicket.ticketId())
                    .numbersFromUser(numbersFromUser)
                    .message("Success")
                    .build();
        }
        return builder().message("Failure").build();
    }

    public List<TicketDto> userNumbers(LocalDateTime date){
        List<Ticket> allTicketsByDrawDate = repository.findAllTicketsByDrawDate(date);
        return allTicketsByDrawDate.stream()
                .map(TicketMapper::mapFromTicket)
                .toList();
    }
}
