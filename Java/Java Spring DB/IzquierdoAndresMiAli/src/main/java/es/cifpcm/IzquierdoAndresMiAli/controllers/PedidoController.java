package es.cifpcm.IzquierdoAndresMiAli.controllers;

import es.cifpcm.IzquierdoAndresMiAli.data.services.PedidoService;
import es.cifpcm.IzquierdoAndresMiAli.data.services.UserService;
import es.cifpcm.IzquierdoAndresMiAli.models.Pedido;
import es.cifpcm.IzquierdoAndresMiAli.models.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import es.cifpcm.IzquierdoAndresMiAli.models.Productoffer;

import java.time.LocalDate;
import java.util.List;

import static es.cifpcm.IzquierdoAndresMiAli.controllers.ProductofferController.carrito;

@Validated
@Controller
@RequestMapping("/pedido")
public class PedidoController {

    private final PedidoService pedidoService;

    private final UserService userService;

    public PedidoController(PedidoService pedidoService, UserService userService) {
        this.pedidoService = pedidoService;
        this.userService = userService;
    }

    @GetMapping
    public String getPedidos(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails userDetails)) {
            return "/";
        }
        List<Pedido> pedidos = pedidoService.getPedidosByUserName(userDetails.getUsername());
        model.addAttribute("pedidos", pedidos);
        return "pedidos";
    }

    @PostMapping
    public String processPurchase() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails userDetails)) {
            return "/";
        }
        userDetails.getUsername();
        Float totalPrice = calculateTotalPrice(carrito);

        Pedido pedido = new Pedido(userDetails.getUsername(), totalPrice, carrito, LocalDate.now());
        pedidoService.save(pedido);
        carrito.clear();

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
