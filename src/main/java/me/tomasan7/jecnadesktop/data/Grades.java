package me.tomasan7.jecnadesktop.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * A class representing grades table. Stores 0 or more grades for each subject.
 */
public class Grades
{
	private final Map<String, List<Grade>> grades;

	/**
	 * Constructor used by {@link Attendances.Builder}.
	 */
	private Grades (Map<String, List<Grade>> grades)
	{
		this.grades = grades;
	}

	/**
	 * Returns all grades for the passed subject.
	 * @param subject The subject to get all grades for.
	 * @return List of all grades for passed subject or {@code null}, if there are no grades for this subject.
	 */
	@Nullable
	public List<Grade> gradesForSubject (String subject)
	{
		return new ArrayList<>(grades.get(subject));
	}

	/**
	 * Returns all subjects.
	 */
	public Set<String> subjects ()
	{
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

		public Grades build ()
		{
			return new Grades(grades);
		}
	}
}
