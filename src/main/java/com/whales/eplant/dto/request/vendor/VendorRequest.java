package com.whales.eplant.dto.request.vendor;

import com.whales.eplant.data.model.Role;
import com.whales.eplant.dto.request.caterer.CatererAttributes;
import com.whales.eplant.dto.request.dj.DjAttributes;
import com.whales.eplant.dto.request.mc.McAttributes;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class VendorRequest {
    private Double price;
    private String description;
    private Double bonus;
    private boolean availability;
    private Role role;

    // Each role has its own attributes class
    private DecoratorAttributes decoratorAttributes;
    private DjAttributes djAttributes;
    private McAttributes mcAttributes;
    private CatererAttributes catererAttributes;




//    private Map<String, Object> roleAttributes;

    public BigDecimal getPriceAsBigDecimal() {
        return price != null ? BigDecimal.valueOf(price) : BigDecimal.ZERO;
    }

    public BigDecimal getBonusBigDecimal() {
        return bonus != null ? BigDecimal.valueOf(bonus) : BigDecimal.ZERO;
    }
]
