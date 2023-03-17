package net.benfro.concalc.web;

import lombok.RequiredArgsConstructor;
import net.benfro.concalc.service.CongestionTaxCalculator;
import net.benfro.concalc.model.DefaultVehicle;
import net.benfro.concalc.web.data.DateList;
import net.benfro.concalc.web.data.TaxResult;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableAutoConfiguration
@RequiredArgsConstructor
public class TaxCalculatorEndpoint {

    private final CongestionTaxCalculator calculator;

    @PostMapping("tax")
    public TaxResult calculateTax(@RequestBody DateList dateList) {
        final int tax = calculator.calculate(DefaultVehicle.of("Bil"), dateList.getDates());
        return new TaxResult(String.valueOf(tax));
    }

    @GetMapping("test")
    public String calculateTaxi() {
        return "Hello World";
    }
}
