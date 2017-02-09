package br.org.ons.platform.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Representa a linha do tempo de uma instância de aggregate, contendo o
 * histórico de registros de todos os comandos e eventos relativos ao aggregate.
 */
@Entity
@Table(name = "tb_timeline", schema="platform")
public class Timeline {

    @Id
    @Column(name = "id_timeline")
    private String id;
    
    /**
     * ID da instância de aggregate
     */
    @Column(name = "id_aggregate")
    private String aggregateId;
    
    /**
     * Nome da classe do aggregate
     */
    @Column(name = "tip_aggregate")
    private String aggregateType;

    /**
     * Número de versão que identifica as timelines de um aggregate
     */
    @Column(name = "ver_major")
    private Long majorVersion;

    /**
     * Indica a timeline principal de um aggregate
     */
    @Column(name = "flg_main")
    private Boolean main;

    /**
	 * No caso de uma timeline branch, identifica a timeline de origem da branch
	 */
    @Column(name = "id_parent")
    private String parentId;
    
	/**
	 * @return o campo id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id o campo id a ser definido
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return o campo aggregateId
	 */
	public String getAggregateId() {
		return aggregateId;
	}

	/**
	 * @param aggregateId o campo aggregateId a ser definido
	 */
	public void setAggregateId(String aggregateId) {
		this.aggregateId = aggregateId;
	}

	/**
	 * @return o campo aggregateType
	 */
	public String getAggregateType() {
		return aggregateType;
	}

	/**
	 * @param aggregateType o campo aggregateType a ser definido
	 */
	public void setAggregateType(String aggregateType) {
		this.aggregateType = aggregateType;
	}

	/**
	 * @return o campo majorVersion
	 */
	public Long getMajorVersion() {
		return majorVersion;
	}

	/**
	 * @param majorVersion o campo majorVersion a ser definido
	 */
	public void setMajorVersion(Long majorVersion) {
		this.majorVersion = majorVersion;
	}

	/**
	 * @return o campo main
	 */
	public Boolean getMain() {
		return main;
	}

	/**
	 * @param main o campo main a ser definido
	 */
	public void setMain(Boolean main) {
		this.main = main;
	}

	/**
	 * @return o campo parentId
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * @param parentId o campo parentId a ser definido
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Timeline timeline = (Timeline) o;
        if(timeline.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, timeline.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Timeline{" +
            "id=" + id +
            ", aggregateId='" + aggregateId + "'" +
            ", majorVersion='" + majorVersion + "'" +
            ", isDefault='" + main + "'" +
            '}';
    }
}
