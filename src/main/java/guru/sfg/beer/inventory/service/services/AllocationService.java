package guru.sfg.beer.inventory.service.services;

import guru.sfg.common.model.BeerOrderDto;

public interface AllocationService {
    Boolean allocateOrder(BeerOrderDto beerOrderDto);
}
