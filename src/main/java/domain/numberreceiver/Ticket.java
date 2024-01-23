package domain.numberreceiver;

import java.time.LocalDateTime;
import java.util.Set;

//encja do bazy danych
record Ticket(String ticketId, LocalDateTime drawDate, Set<Integer> numbersFromUser) {
}
