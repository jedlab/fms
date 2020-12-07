package com.fms.app.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = FolderEntity.DISCRIMINATOR_VALUE)
public class FolderEntity extends DocumentEntity {

	public static final String DISCRIMINATOR_VALUE = "F";

}
