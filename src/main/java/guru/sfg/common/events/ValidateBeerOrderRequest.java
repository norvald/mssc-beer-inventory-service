package guru.sfg.common.events;

import guru.sfg.common.model.BeerOrderDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidateBeerOrderRequest {
    private BeerOrderDto beerOrderDto;
}
