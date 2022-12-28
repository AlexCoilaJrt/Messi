/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pe.edu.upeu.app.dao;

import java.util.List;
import pe.edu.upeu.app.modelo.ProductoTO;


public interface ProductoDaoI {

    public int create(ProductoTO d);

    public int update(ProductoTO d);

    public int delete(String id) throws Exception;

    public List<ProductoTO> listCmb(String filter);

    public List <ProductoTO>listarProducto(); 

    public ProductoTO buscarProducto(String nombreP);

    public void reportarProducto();
}
