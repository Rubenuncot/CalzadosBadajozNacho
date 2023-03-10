package com.ruben.calzadosBadajoz.app.controllers;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.ruben.calzadosBadajoz.app.models.entity.Zapato;
import com.ruben.calzadosBadajoz.app.models.service.IZapatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.ruben.calzadosBadajoz.app.util.paginator.PageRender;

import jakarta.validation.Valid;

@Controller
@SessionAttributes("zapato")
public class ZapatoController {
    @Autowired
    private IZapatoService zapatoService;

    @GetMapping(value = "/ver/{id}")
    public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
        List<Zapato> zapatos = zapatoService.findAllZapatos();
        Zapato zapato = null;

        for (Zapato value : zapatos) {
            if (Objects.equals(value.getId(), id)) {
                zapato = value;
            }
        }
        if (zapato == null) {
            flash.addFlashAttribute("error", "El zapato no se encuentra registrado");
            return "redirect:/listar";
        }
        model.put("zapato", zapato);
        model.put("titulo", "Detalles del zapato: " + zapato.getNombre() + " " + zapato.getMarca());
        return "ver";
    }

    @RequestMapping(value = "/listar", method = RequestMethod.GET)
    public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        Pageable pageRequest = PageRequest.of(page, 5);
        Page<Zapato> zapatos = zapatoService.findAll(pageRequest);
        List<Zapato> zapatosLista = zapatoService.findAllZapatos();

        PageRender<Zapato> pageRender = new PageRender<>("/listar", zapatos);

        model.addAttribute("titulo", "Listado de zapatos");
        model.addAttribute("zapatos", zapatos);
        model.addAttribute("zapatosLista", zapatosLista);
        model.addAttribute("page", pageRender);
        return "listar";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model) {
        model.addAttribute("titulo", "Home");
        return "home";
    }

    @RequestMapping(value = "/form")
    public String crear(Map<String, Object> model) {

        Zapato zapato = new Zapato();
        model.put("zapato", zapato);
        model.put("titulo", "Formulario de Zapatos");
        return "form";
    }

    @RequestMapping(value = "/form/{id}")
    public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

        List<Zapato> zapatos = zapatoService.findAllZapatos();
        Zapato zapato = null;

        for (Zapato value : zapatos) {
            if (Objects.equals(value.getId(), id)) {
                zapato = value;
            }
        }

        if (id > 0) {
            if (zapato == null) {
                flash.addFlashAttribute("error", "El id del zapato no se encuentra en la base de datos.");
                return "redirect:/listar";
            }
        } else {
            flash.addFlashAttribute("error", "El id del zapato no puede ser cero");
            return "redirect:/listar";
        }
        model.put("zapato", zapato);
        model.put("titulo", "Editar Zapato");
        return "form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String guardar(@Valid Zapato zapato, SessionStatus status, RedirectAttributes flash) {
        zapato.setCreateAt(new Date());
        zapatoService.save(zapato);
        status.setComplete();
        flash.addFlashAttribute("msg", "Zapato creado con éxito");
        return "redirect:listar";
    }

    @RequestMapping(value = "/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

        if (id > 0) {
            zapatoService.delete(id);
            flash.addFlashAttribute("success", "Zapato eliminado");
        }
        return "redirect:/listar";
    }

    @GetMapping(value = "/cargarzapatos/{term}", produces = {"application/json"})
    public @ResponseBody List<Zapato> cargarZapatos(@PathVariable String term){
        return zapatoService.findByNombre(term);
    }
}