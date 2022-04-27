package me.tomasan7.jecnadesktop.data;

import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Representing grades table.
 * Stores {@code 0} or more grades for each subject.
 */
public class Grades
{
	private final Map<String, List<Grade>> grades;

	/**
	 * Constructor used by {@link Attendances.Builder}.
	 */
	private Grades (@NotNull Map<String, List<Grade>> grades)
	{
		/* Encapsulation. */
		this.grades = new HashMap<>(grades);
	}

	/**
	 * @return List of all {@link Grade grades} for passed subject. Can be empty, if no grades are present for that subject.
	 */
	@NotNull
	public List<Grade> getGradesForSubject (String subject)
	{
		/* Encapsulation. */
		return new LinkedList<>(grades.get(subject));
	}

	/**
	 * @return All subjects.
	 */
	@NotNull
	public Set<String> getSubjects ()
	{
		/* Encapsulation. */
		return new HashSet<>(grades.keySet());
	}

	public static Builder builder ()
	{
		return new Builder();
	}

	public static class Builder
	{
		private final Map<String, List<Grade>> grades = new HashMap<>();

		/**
		 * Adds {@link Grade}.
		 * @param subject The subject to add this grade to. <b>Must be the same format is in the grades table rows.</b>
		 * @param grade The {@link Grade} to add.
		 */
		public void addGrade (@NotNull String subject, @NotNull Grade grade)
		{
			/* Gets the list for the subject, if none is present, creates a new list and puts it into the map. Then the grade is added to that list. */
			grades.computeIfAbsent(subject, __ -> new LinkedList<>()).add(grade);
		}

		@NotNull
		public Grades build ()
		{
			return new Grades(grades);
		}
	}
}
