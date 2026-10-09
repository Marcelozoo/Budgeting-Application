package dio.budgeting.auth.infrastructure.persistence.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_role")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;


    private String name;


    public enum Values{

        ADMIN(1L),
        BASIC(2L);

        long roleId;

        Values(long id){
            this.roleId = id;
        }
    }


}
