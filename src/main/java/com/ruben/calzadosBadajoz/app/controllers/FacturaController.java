package com.ruben.calzadosBadajoz.app.controllers;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ruben.calzadosBadajoz.app.models.entity.Cliente;
import com.ruben.calzadosBadajoz.app.models.entity.Factura;
import com.ruben.calzadosBadajoz.app.models.entity.ItemFactura;
import com.ruben.calzadosBadajoz.app.models.entity.Zapato;
import com.ruben.calzadosBadajoz.app.models.service.IClienteService;
import com.ruben.calzadosBadajoz.app.models.service.IFacturaService;
import com.ruben.calzadosBadajoz.app.models.service.IZapatoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/factura")
@SessionAttributes("factura")
public class FacturaController {
    @Autowired
    private IClienteService clienteService;
    @Autowired
    private IFacturaService facturaService;
    @Autowired
    private IZapatoService zapatoService;
    Cliente cliente;
    @PostMapping(value = "/forminfo")
    public String guardar(@Valid Factura factura, SessionStatus status, BindingResult result, RedirectAttributes flash, @RequestParam(name="item_id[]",required = false)Long[] itemId,
                          @RequestParam(name="cantidad[]",required = false)Integer[] cantidad) {
        for (int i = 0; i < itemId.length; i++) {
            Zapato zapato = clienteService.findZapatoById(itemId[i]);
            ItemFactura linea = new ItemFactura();
            linea.setCantidad(cantidad[i]);
            linea.setProducto(zapato);
            factura.addItemFactura(linea);
        }
        factura.setCreateAt(new Date());
        facturaService.save(factura);
        status.setComplete();
        flash.addFlashAttribute("msg", "Factura creada con éxito");
        return "redirect:listarClientes";
    }
    @RequestMapping(value = "/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

        if (id > 0) {
            facturaService.delete(id);
            flash.addFlashAttribute("success", "Factura eliminada");
        }
        return "redirect:/listarClientes";
    }
    @GetMapping("/form/{clienteId}")
    public String crear(@PathVariable(value="clienteId") Long clienteId, Map<String,Object> model,
                        RedirectAttributes flash) {
        if (getClientesFormFactura(clienteId, model, flash)) return "redirect:/listarclientes";
        model.put("titulo", "Creando la factura");

        return "factura/form";
    }

    private boolean getClientesFormFactura(@PathVariable("clienteId") Long clienteId, Map<String, Object> model, RedirectAttributes flash) {
         cliente =  clienteService.findOne(clienteId);
        if(cliente==null) {
            flash.addFlashAttribute("error","El cliente no existe en la base de datos");
            return true;
        }
        List<Zapato> zapatos = zapatoService.findAllZapatos();
        Factura factura = new Factura();
        factura.setCliente(cliente);

        model.put("factura", factura);
        model.put("cliente", cliente);
        model.put("zapato", zapatos);
        return false;
    }
    @GetMapping(value= "/cargar-zapatos/{term}", produces= {"application/json"})
    public @ResponseBody List<Zapato> cargarProductos(@PathVariable String term) {
        return clienteService.findByNombre(term);
    }
    @GetMapping("/forminfo/{clienteId}")
    public String crearinfo(@PathVariable(value="clienteId") Long clienteId, Map<String, Object> modelmap,
                            RedirectAttributes flash, Model model) {
        if (getClientesFormFactura(clienteId, modelmap, flash)) return "redirect:/listarclientes";
        modelmap.put("titulo", "Información y creación de la factura para el cliente " + cliente.getNombre());


        return "factura/forminfo";
    }







}

