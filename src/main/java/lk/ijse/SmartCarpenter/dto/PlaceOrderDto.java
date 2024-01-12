package lk.ijse.SmartCarpenter.dto;

import lk.ijse.SmartCarpenter.dto.tm.CartTm;

import java.util.ArrayList;
import java.util.List;


public class PlaceOrderDto {

    private OrderDto dto;
    private List<CartTm> list = new ArrayList<>();

    public PlaceOrderDto(){

    }

    public PlaceOrderDto(OrderDto dto, List<CartTm> list) {
        this.dto = dto;
        this.list = list;
    }

    public OrderDto getDto() {
        return dto;
    }

    public List<CartTm> getList() {
        return list;
    }

    public void setDto(OrderDto dto) {
        this.dto = dto;
    }

    public void setList(List<CartTm> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "PlaceOrderDto{" +
                "dto=" + dto +
                ", list=" + list +
                '}';
    }
}
