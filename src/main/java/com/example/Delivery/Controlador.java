/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Delivery;

import com.example.Delivery.Cliente.Cliente;
import com.example.Delivery.Cliente.IClienteService;
import com.example.Delivery.Cliente.Mail;
import com.example.Delivery.Distrito.Distrito;
import com.example.Delivery.Distrito.IDistritoService;
import com.example.Delivery.Pedido.Pedido;
import com.example.Delivery.Producto.IProductoService;
import com.example.Delivery.Producto.Producto;
import com.example.Delivery.ProductoPedido.ProductoPedido;
import com.example.Delivery.ProductoPedido.ProductoPedidoID;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author INTEL
 */
@Controller
public class Controlador {

    @Autowired
    private IProductoService serviceProductos;

    @Autowired
    private IClienteService serviceClientes;

    @Autowired
    private IDistritoService serviceDistritos;

    List<Cliente> clientes = new ArrayList();
    Cliente clienteSesion;
    Pedido pedidoAux = new Pedido();
    EstadoConexion conex = new EstadoConexion();

    int posCliente = 0;
    int posCuenta = 0;
    int posProducto = 0;
    double monto_total = 0;

    /*------Menu inicio------*/
    @GetMapping("/")
    public String inicio(Model model) {
        model.addAttribute("conexion", this.conex);
        model.addAttribute("client", this.clienteSesion);
        model.addAttribute("pedido", this.pedidoAux);
        model.addAttribute("monto", this.monto_total);
        return "MenuPrincipal";
    }

    /*------Inicio de sesión------*/
    @GetMapping("/logueo")
    public String login() {
        this.posCliente = 0;
        this.posProducto = 0;
        this.clientes = serviceClientes.listar();
        return "login";
    }

    /*------Menu ubicación - Google Maps------*/
    @GetMapping("/ubicacion")
    public String ubicacion(Model model) {
        model.addAttribute("client", this.clienteSesion);
        model.addAttribute("conexion", this.conex);
        model.addAttribute("pedido", this.pedidoAux);
        model.addAttribute("monto", this.monto_total);
        return "Ubicanos";
    }

    /*------Menu Carta(Productos)------*/
    @GetMapping("/carta")
    public String carta(Model model) {
        List<Producto> productos = this.serviceProductos.listar();
        model.addAttribute("productos", productos);
        model.addAttribute("client", this.clienteSesion);
        model.addAttribute("conexion", this.conex);
        model.addAttribute("pedido", this.pedidoAux);
        model.addAttribute("monto", this.monto_total);
        return "Carta";
    }

    /*------Función que valida las credenciales del Cliente/Usuario------*/
    @RequestMapping(value = "/ingresar", method = RequestMethod.POST)
    public String ingresar(@RequestParam("usuario") String user, @RequestParam("clave") String clave, Model model) {
        if (buscarCliente(user, clave)) {
            clienteSesion = this.clientes.get(this.posCliente);
            this.conex.setEstado("on");
            model.addAttribute("conexion", this.conex);
            model.addAttribute("client", this.clienteSesion);
            model.addAttribute("pedido", this.pedidoAux);
            model.addAttribute("monto", this.monto_total);
            return "MenuPrincipal";
        } else {
            return "Login";
        }
    }

    /*------Menu - Pedido del Cliente------*/
    @GetMapping("/pedidoM")
    public String menuPedido(Model model) {
        List<Distrito> distritos = this.serviceDistritos.listar();
        model.addAttribute("distritos", distritos);

        model.addAttribute("produc", this.pedidoAux.getListaProductosPedidos());
        model.addAttribute("pedido", this.pedidoAux);
        model.addAttribute("client", this.clienteSesion);
        model.addAttribute("conexion", this.conex);
        model.addAttribute("monto", this.monto_total);
        return "Pedido";
    }

    /*------Agrega un producto selecionado al pedido del cliente------*/
    @GetMapping("/pedido")
    public String agregarAlPedido(@RequestParam(value = "id") int id, Model model) {
        List<Producto> productos = serviceProductos.listar();
        cargarAlPedido(buscarProducto(productos, id));

        List<Distrito> distritos = this.serviceDistritos.listar();
        model.addAttribute("distritos", distritos);
        model.addAttribute("produc", this.pedidoAux.getListaProductosPedidos());
        model.addAttribute("pedido", this.pedidoAux);
        model.addAttribute("client", this.clienteSesion);
        model.addAttribute("conexion", this.conex);
        model.addAttribute("monto", this.monto_total);
        return "Pedido";
    }

    /*------Funcion que valida si un producto se encuentra en el pedido del cliente------*/
    private void cargarAlPedido(Producto p) {
        if (productoRepetido(p)) {
            int cant = this.pedidoAux.getListaProductosPedidos().get(posProducto).getCantidad() + 1;
            this.monto_total += p.getPrecio_unitario();
            this.pedidoAux.getListaProductosPedidos().get(this.posProducto).setCantidad(cant);
        } else {
            ProductoPedido productoAux = new ProductoPedido(1, p.getPrecio_unitario(), p);
            ProductoPedidoID id = new ProductoPedidoID(p.getId_producto());
            productoAux.setId(id);
            this.pedidoAux.addProducto(productoAux);
            this.monto_total += p.getPrecio_unitario();
        }
    }

    /*------Modifica la cantidad del producto selecionado------*/
    @GetMapping("/cantidad")
    public String cantidadMod(@RequestParam(value = "id") int id, @RequestParam(value = "cantidad") int cantidad, @RequestParam(value = "tipo") int tipo, Model model) {
        int pos = buscarProductoPos(id);
        if (tipo == 1) {//resta
            int cant = this.pedidoAux.getListaProductosPedidos().get(pos).getCantidad() - 1;
            this.monto_total -= this.pedidoAux.getListaProductosPedidos().get(pos).getPrecio_unitario();
            this.pedidoAux.getListaProductosPedidos().get(pos).setCantidad(cant);
        } else {//suma
            int cant = this.pedidoAux.getListaProductosPedidos().get(pos).getCantidad() + 1;
            this.monto_total += this.pedidoAux.getListaProductosPedidos().get(pos).getPrecio_unitario();
            this.pedidoAux.getListaProductosPedidos().get(pos).setCantidad(cant);
        }
        List<Distrito> distritos = this.serviceDistritos.listar();
        model.addAttribute("distritos", distritos);
        model.addAttribute("produc", this.pedidoAux.getListaProductosPedidos());
        model.addAttribute("pedido", this.pedidoAux);
        model.addAttribute("client", this.clienteSesion);
        model.addAttribute("conexion", this.conex);
        model.addAttribute("monto", this.monto_total);

        return "Pedido";
    }

    /*------Confirmacion del Pedido------*/
    @RequestMapping(value = "/comprar", method = RequestMethod.POST)
    public String comprarProductos(@RequestParam("distrito") String distrito, @RequestParam("direccion") String direccion, @RequestParam("tipo_pago") String tipo_pago, @RequestParam("destinatario") String destinatario, Model model) {
        String dir = distrito + " - " + direccion;
        this.pedidoAux.setDireccion(dir);
        long now = System.currentTimeMillis();
        this.pedidoAux.setFecha_pedido(new Date(now));
        this.pedidoAux.setNombre_destinatario(destinatario);
        this.pedidoAux.setTipo_pago(tipo_pago);
        this.pedidoAux.setEstado("Pendiente");
        this.clienteSesion.addPedido(this.pedidoAux);
        this.serviceClientes.Guardar(this.clienteSesion);
        this.pedidoAux = new Pedido();
        this.monto_total = 0;
        model.addAttribute("conexion", this.conex);
        model.addAttribute("client", this.clienteSesion);
        model.addAttribute("pedido", this.pedidoAux);
        model.addAttribute("monto", this.monto_total);
        return "menuPrincipal";
    }

    /*------Eliminar un producto de un pedido------*/
    @GetMapping("/remover")
    public String EliminarP(@RequestParam(value = "id") int id, Model model) {
        for (int i = 0; i < this.pedidoAux.getListaProductosPedidos().size(); i++) {
            if (this.pedidoAux.getListaProductosPedidos().get(i).getId().getId_producto() == id) {
                this.monto_total -= (this.pedidoAux.getListaProductosPedidos().get(i).getCantidad() * this.pedidoAux.getListaProductosPedidos().get(i).getPrecio_unitario());
                this.pedidoAux.getListaProductosPedidos().remove(i);
                break;
            }
        }
        List<Distrito> distritos = this.serviceDistritos.listar();
        model.addAttribute("distritos", distritos);
        model.addAttribute("produc", this.pedidoAux.getListaProductosPedidos());
        model.addAttribute("pedido", this.pedidoAux);
        model.addAttribute("client", this.clienteSesion);
        model.addAttribute("conexion", this.conex);
        model.addAttribute("monto", this.monto_total);
        return "Pedido";
    }

    /*------Menu Registrar Cliente------*/
    @GetMapping("/create")
    public String pgPrincipal(Model model) {
        List<Distrito> distritos = this.serviceDistritos.listar();
        model.addAttribute("distritos", distritos);
        model.addAttribute("cliente", new Cliente());
        return "RegistrarCliente";
    }

    /*@RequestMapping(value = "/registrarUsuario", method = RequestMethod.POST)
    public String registroCliente(@RequestParam("nombres") String nombres, @RequestParam("apellidos") String apellidos, @RequestParam("dni") String dni, @RequestParam("correo") String correo, @RequestParam("telefono") String telefono, @RequestParam("distrito") String distrito, @RequestParam("direccion") String direccion, @RequestParam("usuario") String usuario, @RequestParam("clave") String clave, Model model) {
        List<Distrito> distritos = serviceDistritos.listar();
        Cliente c = new Cliente(nombres, apellidos, dni, correo, telefono, buscarDistrito(distritos, distrito), direccion, usuario, clave);
        serviceClientes.Guardar(c);

        this.clientes = serviceClientes.listar();
        if (buscarCliente(c.getUsuario(), c.getClave())) {
            clienteSesion = this.clientes.get(posCliente);
            this.conex.setEstado("on");

            model.addAttribute("conexion", this.conex);
            model.addAttribute("client", this.clienteSesion);
            model.addAttribute("pedido", this.pedidoAux);
            model.addAttribute("monto", this.monto_total);
            return "MenuPrincipal";
        } else {
            return "Login";
        }
    }*/
    
    /*------Registro de Usuario/Cliente y Logueo------*/
    @RequestMapping(value = "/registrarUsuarioP", method = RequestMethod.POST)
    public String registroClienteP(Model model, @ModelAttribute("cliente") Cliente cliente) {
        System.out.println(cliente.getApellidos());
        System.out.println(cliente.getDistrito().getNombre_distrito());
        serviceClientes.Guardar(cliente);

        this.clientes = serviceClientes.listar();
        if (buscarCliente(cliente.getUsuario(), cliente.getClave())) {
            System.out.println("Ingreso a esta parte");
            clienteSesion = this.clientes.get(posCliente);
            List<Producto> platos = this.serviceProductos.listarCategoria(1);
            List<Producto> bebidas = this.serviceProductos.listarCategoria(2);

            this.conex.setEstado("on");

            model.addAttribute("conexion", this.conex);
            model.addAttribute("client", this.clienteSesion);
            model.addAttribute("platos", platos);
            model.addAttribute("bebidas", bebidas);
            model.addAttribute("pedido", this.pedidoAux);
            model.addAttribute("monto", this.monto_total);
            return "MenuPrincipal";
        } else {
            return "Login";
        }
    }

    /*------Cerrar sesion del usuario------*/
    @GetMapping("/cerrarSesion")
    public String finSesion(Model model) {
        this.conex.setEstado("off");
        model.addAttribute("client", this.clienteSesion);
        model.addAttribute("conexion", this.conex);
        model.addAttribute("pedido", this.pedidoAux);
        model.addAttribute("monto", this.monto_total);
        return "MenuPrincipal";
    }

    /*------Datos del usuario------*/
    @GetMapping("/miCuenta")
    public String menuCuenta(Model model) {
        model.addAttribute("client", this.clienteSesion);
        return "MisDatos";
    }

    /*------Historial de pedidos realizados------*/
    @GetMapping("/misPedidos")
    public String historialPedidos(Model model) {
        this.clientes = this.serviceClientes.listar();
        clienteSesion = this.clientes.get(posCliente);
        model.addAttribute("client", this.clienteSesion);
        model.addAttribute("conexion", this.conex);
        model.addAttribute("pedidos", this.clienteSesion.getLpedido());
        model.addAttribute("pedido", this.pedidoAux);
        model.addAttribute("monto", this.monto_total);
        return "MisPedidos";
    }

    /*------Menu Recuperación de contraseña------*/
    @GetMapping("/recuperarCuenta")
    public String pgRecuperar() {
        return "RecuperarCuenta";
    }

    /*------Envio de credenciales de acceso de un usuario mediante su email------*/
    @RequestMapping(value = "/recuperar", method = RequestMethod.POST)
    public String recuperarCuentaEnvio(@RequestParam("dni") String dni, Model model) {
        this.clientes = serviceClientes.listar();
        if (buscarCuenta(dni)) {
            Mail mail = new Mail();
            mail.enviarCorreo(this.clientes.get(posCuenta).getCorreo(), this.clientes.get(posCuenta).getUsuario(), this.clientes.get(posCuenta).getClave());
            return "Login";
        } else {
            return "RecuperarCuenta";
        }
    }

    /*------Busqueda de la cuenta por dni------*/
    public boolean buscarCuenta(String dni) {
        boolean rpta = false;
        for (int i = 0; i < this.clientes.size(); i++) {
            if (this.clientes.get(i).getDni().equals(dni)) {
                posCuenta = i;
                rpta = true;
            }
        }
        return rpta;
    }

    /*------Busqueda del cliente para el logueo------*/
    public boolean buscarCliente(String user, String clave) {
        boolean rpta = false;
        for (int i = 0; i < this.clientes.size(); i++) {
            if (this.clientes.get(i).getUsuario().equals(user) && this.clientes.get(i).getClave().equals(clave)) {
                posCliente = i;
                rpta = true;
            }
        }
        return rpta;
    }

    /*------Busqueda de Distrito------*/
    public Distrito buscarDistrito(List<Distrito> distritos, String buscado) {
        Distrito ds = new Distrito();
        for (Distrito distrito : distritos) {
            if (buscado.equals(distrito.getNombre_distrito())) {
                ds = distrito;
                break;
            }
        }
        return ds;
    }

    /*public Distrito buscarDistrito(List<Distrito> distritos, String buscado) {
        Distrito ds = new Distrito();
        for (int i = 0; i < distritos.size(); i++) {
            if (buscado.equals(distritos.get(i).getNombre_distrito())) {
                ds = distritos.get(i);
                break;
            }
        }
        return ds;
    }*/
    /*------Busqueda de Producto------*/
    public Producto buscarProducto(List<Producto> productos, int buscado) {
        Producto prod = new Producto();
        for (Producto producto : productos) {
            if (buscado == producto.getId_producto()) {
                prod = producto;
                break;
            }
        }
        return prod;
    }

    /*public Producto buscarProducto(List<Producto> productos, int buscado) {
        Producto prod = new Producto();
        for (int i = 0; i < productos.size(); i++) {
            if (buscado == productos.get(i).getId_producto()) {
                prod = productos.get(i);
                break;
            }
        }
        return prod;
    }*/

    /*------Busqueda de la posicion de un producto ------*/
    public int buscarProductoPos(int id) {
        int posicion = 0;
        for (int i = 0; i < this.pedidoAux.getListaProductosPedidos().size(); i++) {
            if (this.pedidoAux.getListaProductosPedidos().get(i).getId().getId_producto() == id) {
                posicion = i;
                return posicion;
            }
        }
        return posicion;
    }

    /*------Busqueda de Producto en un pedido------*/
    public boolean productoRepetido(Producto p) {
        boolean rpta = false;
        for (int i = 0; i < this.pedidoAux.getListaProductosPedidos().size(); i++) {
            if (this.pedidoAux.getListaProductosPedidos().get(i).getProducto().getId_producto() == p.getId_producto()) {
                this.posProducto = i;
                rpta = true;
            }
        }
        return rpta;
    }
}
