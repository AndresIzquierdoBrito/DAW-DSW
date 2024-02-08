package es.cifpcm.IzquierdoAndresMiAli.data.services;

import es.cifpcm.IzquierdoAndresMiAli.data.repositories.PedidosRepository;
import es.cifpcm.IzquierdoAndresMiAli.models.Pedido;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PedidoService {

    private final PedidosRepository pedidosRepository;

    public PedidoService(PedidosRepository pedidosRepository) {
        this.pedidosRepository = pedidosRepository;
    }

    public void save(Pedido pedido) {
        Pedido bean = new Pedido();
        BeanUtils.copyProperties(pedido, bean);
        pedidosRepository.save(bean);
    }

    public List<Pedido> getAllPedidos(){
        return pedidosRepository.findAll();
    }

    public List<Pedido> getPedidosByUserName(String userName){
        return pedidosRepository.findByUsuario(userName);
    }

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
