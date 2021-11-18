package com.br.refera.sysorder.repository;

import com.br.refera.sysorder.entity.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
//	List<Role> findByGrupos(Grupo grupo);

//    @Query(value = "select ro.* from role ro " +
//            "inner join usuario_role ur where ur.id_usuario = :idUsuario", nativeQuery = true)
//    List<Role> findByIdUsuario(String idUsuario);
//
//    @Query(value = "select ro.* from role ro " +
//            "inner join usuario_role ur " +
//            "where ur.id_usuario = :idUsuario and ur.id_clinica = :idClinica", nativeQuery = true)
//    List<Role> findByIdUsuarioAndIdClinica(String idUsuario, String idClinica);

}
