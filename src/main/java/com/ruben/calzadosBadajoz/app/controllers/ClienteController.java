package com.ruben.calzadosBadajoz.app.controllers;


import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;
import com.ruben.calzadosBadajoz.app.models.entity.Cliente;
import com.ruben.calzadosBadajoz.app.models.service.IClienteService;
import com.ruben.calzadosBadajoz.app.models.service.IUploadFileService;
import com.ruben.calzadosBadajoz.app.util.paginator.PageRender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@SessionAttributes("cliente")
public class ClienteController {

    @Autowired
    private IClienteService clienteService;
    @Autowired
    private IUploadFileService uploadFileService;

    @GetMapping(value="/uploads/{filename:.+}")
    public ResponseEntity<Resource> verFoto(@PathVariable String filename){
        Resource recurso=null;
        try {
            recurso=uploadFileService.load(filename);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+recurso.getFilename()+"\"").body(recurso);
    }
    @GetMapping(value="/verclientes/{id}")
    public String verClientes(@PathVariable(value="id") Long id,Map<String, Object> model,RedirectAttributes flash) {

        Cliente cliente=clienteService.findOne(id);
        if(cliente==null) {
            flash.addFlashAttribute("error","El cliente no existe en nuestra BBDD");
            return "redirect:/listar";
        }
        model.put("cliente", cliente);
        model.put("titulo", "Detalles del Cliente: "+cliente.getNombre()+" "+cliente.getApellido());
        return "verclientes";
    }

    @RequestMapping(value = "/listarclientes", method = RequestMethod.GET)
    public String listarClientes(@RequestParam(name="page",defaultValue="0") int page,Model model) {
        Pageable pageRequest= PageRequest.of(page,5);
        Page<Cliente> clientes= clienteService.findAll(pageRequest);
        List<Cliente> clientesList = clienteService.findAll();

        PageRender<Cliente> pageRender= new PageRender<>("/listarclientes",clientes);

        model.addAttribute("titulo", "Listado de clientes por página");
        model.addAttribute("clientes", clientes);
        model.addAttribute("page",pageRender);
        model.addAttribute("clientesList", clientesList);
        return "listarClientes";
    }

    @RequestMapping(value = "/formclientes")
    public String crearClientes(Map<String, Object> model) {

        Cliente cliente = new Cliente();
        model.put("cliente", cliente);
        model.put("titulo", "Formulario de Cliente");
        return "formclientes";
    }

    @RequestMapping(value = "/formclientes/{id}")
    public String editarClientes(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

        Cliente cliente = null;

        if (id > 0) {
            cliente = clienteService.findOne(id);
            if (cliente == null) {
                flash.addFlashAttribute("error", "El ID del cliente no existe en la BBDD!");
                return "redirect:/listarclientes";
            }
        } else {
            flash.addFlashAttribute("error", "El ID del cliente no puede ser cero!");
            return "redirect:/listar";
        }
        model.put("cliente", cliente);
        model.put("titulo", "Editar Cliente");
        return "formclientes";
    }

    @RequestMapping(value = "/formclientes", method = RequestMethod.POST)
    public String guardarclientes(@Valid Cliente cliente, BindingResult result, Model model, RedirectAttributes flash, SessionStatus status,@RequestParam("file") MultipartFile foto) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Formulario de Cliente");
            return "formclientes";
        }
        if(!foto.isEmpty()) {
            if(cliente.getId()!=null
                    && cliente.getId()>0
                    && cliente.getFoto().length()>0) {
                uploadFileService.delete(cliente.getFoto());

            }
            String uniqueFilename=null;
            try {
                uniqueFilename=uploadFileService.copy(foto);
            } catch (IOException e) {
                e.printStackTrace();
            }
            flash.addFlashAttribute("info","Has subido correctamente"+ uniqueFilename);
            cliente.setFoto(uniqueFilename);
        }

        String mensajeFlash = (cliente.getId() != null) ? "Cliente editado con éxito!" : "Cliente creado con éxito!";

        clienteService.save(cliente);
        status.setComplete();
        flash.addFlashAttribute("msg", mensajeFlash);
        return "redirect:listarclientes";
    }

    @RequestMapping(value = "/eliminarclientes/{id}")
    public String eliminarClientes(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

        if (id > 0) {
            Cliente cliente = clienteService.findOne(id);
            clienteService.delete(id);
            if (uploadFileService.delete(cliente.getFoto())){
                flash.addFlashAttribute("info", "Eliminada la foto: " + cliente.getFoto());
            }
        }
        return "redirect:/listarClientes";
    }
}