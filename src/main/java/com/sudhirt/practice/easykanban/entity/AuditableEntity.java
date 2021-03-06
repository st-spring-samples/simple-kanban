package com.sudhirt.practice.easykanban.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

/**
 * AuditableEntity
 */
@Getter
@EntityListeners(AuditingEntityListener.class)
public class AuditableEntity {

	@CreatedBy
	protected String createdBy;

	@CreatedDate
	@Column(columnDefinition = "TIMESTAMP")
	protected LocalDateTime createdAt;

	@LastModifiedBy
	protected String updatedBy;

	@LastModifiedDate
	@Column(columnDefinition = "TIMESTAMP")
	protected LocalDateTime updatedAt;

}