package si.fri.rso.team10.dto;

import si.fri.rso.team10.IdentifiableEntity;

public class BasicDTO {

    protected Long id;

    public BasicDTO(IdentifiableEntity identifiableEntity) {
        this.id = identifiableEntity.getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
