package es.cifpcm.IzquierdoAndresMiAli.data.services;

import es.cifpcm.IzquierdoAndresMiAli.data.repositories.PedidosRepository;
import es.cifpcm.IzquierdoAndresMiAli.models.Pedido;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PedidoService {

    @Autowired
    private PedidosRepository pedidosRepository;

    public Integer save(Pedido pedido) {
        Pedido bean = new Pedido();
        BeanUtils.copyProperties(pedido, bean);
        bean = pedidosRepository.save(bean);
        return bean.getId();
    }

    public List<Pedido> getAllPedidos(){
        return pedidosRepository.findAll();   }

    public void delete(Integer id) {
        pedidosRepository.deleteById(id);
    }

    public void update(Integer id, Pedido pedido) {
        Pedido bean = requireOne(id);
        BeanUtils.copyProperties(pedido, bean);
        pedidosRepository.save(bean);
    }

    public Pedido getById(Integer id) {
        Pedido original = requireOne(id);
        return toDTO(original);
    }

    private Pedido toDTO(Pedido original) {
        Pedido bean = new Pedido();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private Pedido requireOne(Integer id) {
        return pedidosRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
