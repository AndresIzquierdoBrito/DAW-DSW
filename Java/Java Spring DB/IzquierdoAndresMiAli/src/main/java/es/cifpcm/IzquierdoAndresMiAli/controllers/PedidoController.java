package es.cifpcm.IzquierdoAndresMiAli.controllers;

import es.cifpcm.IzquierdoAndresMiAli.data.services.PedidoService;
import es.cifpcm.IzquierdoAndresMiAli.models.Pedido;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import es.cifpcm.IzquierdoAndresMiAli.models.Productoffer;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static es.cifpcm.IzquierdoAndresMiAli.controllers.ProductofferController.carrito;

@Validated
@Controller
@RequestMapping("/pedido")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public String getPedidios(Model model) {
        List<Pedido> pedidos = pedidoService.getAllPedidos();
        model.addAttribute("pedidos", pedidos);
        return "pedidos";
    }

    @PostMapping
    public String processPurchase() {
        String username = "Andres";
        Float totalPrice = calculateTotalPrice(carrito);

        Pedido pedido = new Pedido(username, totalPrice, carrito, LocalDate.now());
        pedidoService.save(pedido);

        return "redirect:/pedido";
    }

    private Float calculateTotalPrice(List<Productoffer> carrito) {
        return carrito.stream()
                .map(Productoffer::getProductPrice)
                .reduce(0f, Float::sum);
    }

    @DeleteMapping("/{id}")
    public void delete(@Valid @NotNull @PathVariable("id") Integer id) {
        pedidoService.delete(id);
    }

    @PutMapping("/{id}")
    public void update(@Valid @NotNull @PathVariable("id") Integer id,
                       @Valid @RequestBody Pedido pedido) {
        pedidoService.update(id, pedido);
    }

    @GetMapping("/{id}")
    public Pedido getById(@Valid @NotNull @PathVariable("id") Integer id) {
        return pedidoService.getById(id);
    }

}
