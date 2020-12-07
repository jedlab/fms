package com.fms.app.domain;

import java.io.InputStream;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.jedlab.framework.spring.dao.PO;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "document", uniqueConstraints = @UniqueConstraint(columnNames = { "uuid" }))
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@SecondaryTable(name = "doc_access", pkJoinColumns = @PrimaryKeyJoinColumn(name = "doc_id", referencedColumnName = "id"))
@Getter
@Setter
@DiscriminatorValue(value = "D")
@DiscriminatorColumn(name = "discriminator", discriminatorType = DiscriminatorType.STRING)
public class DocumentEntity extends PO {

	@Column(name = "name")
	private String name;

	@Column(name = "size")
	private long size;

	@Column(name = "uuid")
	private String uuid;

	@Column(name = "download_count")
	private long downloadCount;

	@Column(name = "discriminator", updatable = false, insertable = false)
	private String discriminator;

	@Column(name = "is_public")
	private boolean publicDocument;

	@Column(name = "passwd")
	private String passwd;

	@Column(table = "doc_access", name = "doc_id", updatable = false, insertable = false)
	private Long docId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", table = "doc_access")
	private UserEntity owner;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id", table = "doc_access")
	private DocumentEntity parent;

	@Enumerated(EnumType.STRING)
	@Column(table = "doc_access", name = "type")
	private Type type;

	@Column(table = "doc_access", name = "favourite")
	private boolean favourite;

	@Column(name = "permission", table = "doc_access")
	@Enumerated(EnumType.STRING)
	private Permission permission;
	
	@Column(name="file_path")
	private String filePath;
	
	@Transient
	private InputStream data;

	public enum Type {
		HOME, SHARE, TRASH;
	}

	public enum Permission {
		OWNER, EDIT, DELETE, VIEW
	}

	public DocumentEntity() {
	}

	public DocumentEntity(Long id) {
		setId(id);
	}

	@Transient
	public boolean isFolder() {
		return getDiscriminator().equals(FolderEntity.DISCRIMINATOR_VALUE);
	}

	@Transient
	public boolean isSharedByType() {
		return getType().equals(Type.SHARE);
	}

}
