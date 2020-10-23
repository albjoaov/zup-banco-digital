package br.com.zup.bootcamp.bancodigital.validators;

import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UniqueValidator implements ConstraintValidator<Unique, Object> {

	private final EntityManager entityManager;
	private Class<?> entityClass;
	private String entityField;

	public UniqueValidator (EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public void initialize(Unique constraintAnnotation) {
		this.entityClass = constraintAnnotation.entityClass();
		this.entityField = constraintAnnotation.entityField();
	}

	@Override
	@Transactional
	public boolean isValid (Object propValueThatMustBeUnique, ConstraintValidatorContext context) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();

		Root<?> from = criteriaQuery.from(entityClass);
		Path<Object> columnThatMustBeUnique = from.get(this.entityField);
		Predicate condition = criteriaBuilder.equal(columnThatMustBeUnique, propValueThatMustBeUnique);

		CriteriaQuery<Object> queryWithWhere = criteriaQuery.select(from).where(condition);

		try {
			this.entityManager.createQuery(queryWithWhere).setMaxResults(1).getSingleResult();
			return false;
		} catch (NoResultException e) {
			return true;
		}
	}
}
