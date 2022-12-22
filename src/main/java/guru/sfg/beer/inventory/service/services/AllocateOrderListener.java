package guru.sfg.beer.inventory.service.services;

import guru.sfg.beer.inventory.service.config.JmsConfig;
import guru.sfg.brewery.model.events.AllocateOrderRequest;
import guru.sfg.brewery.model.events.AllocateOrderResult;
import guru.sfg.brewery.model.BeerOrderDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AllocateOrderListener {
    private final AllocationService allocationService;
    private final JmsTemplate jmsTemplate;

    @Transactional
    @JmsListener(destination = JmsConfig.ALLOCATE_ORDER_QUEUE)
    public void listen(AllocateOrderRequest event) {
        BeerOrderDto beerOrderDto = event.getBeerOrderDto();

        AllocateOrderResult.AllocateOrderResultBuilder builder = AllocateOrderResult.builder();

        builder.beerOrderDto(beerOrderDto);
        try {
            Boolean allocationResult = allocationService.allocateOrder(beerOrderDto);

            if(allocationResult) {
                builder.pendingInventory(false);
            } else {
                builder.pendingInventory(true);
            }
            builder.allocationError(false);
        } catch(Exception e) {
            log.error("Allocation failed: "+beerOrderDto.getId()+ ", " +e.getMessage());
            builder.allocationError(true);
        }
        jmsTemplate.convertAndSend(JmsConfig.ALLOCATE_ORDER_RESPONSE_QUEUE, builder.build());
    }
}
