package me.tomasan7.jecnadesktop.ui.subpage;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import me.tomasan7.jecnadesktop.JecnaDesktop;
import me.tomasan7.jecnadesktop.data.LessonPeriod;
import me.tomasan7.jecnadesktop.data.LessonSpot;
import me.tomasan7.jecnadesktop.data.Timetable;
import me.tomasan7.jecnadesktop.repository.TimetableRepository;
import me.tomasan7.jecnadesktop.ui.CachedPage;
import me.tomasan7.jecnadesktop.ui.component.LessonPeriodView;
import me.tomasan7.jecnadesktop.ui.component.LessonSpotView;

import java.util.List;

public class TimetableSubPage extends CachedPage
{
	private final TimetableRepository timetableRepository;

	public TimetableSubPage (TimetableRepository timetableRepository)
	{
		this.timetableRepository = timetableRepository;
	}

	@Override
	protected Parent createContent ()
	{
		GridPane grid = createGrid();

		timetableRepository.queryTimetableAsync().thenAccept(timetable -> Platform.runLater(() -> populateData(timetable, grid)));

		return grid;
	}

	private GridPane createGrid ()
	{
		GridPane grid = new GridPane();

		grid.setHgap(1);
		grid.setVgap(1);

		grid.getStyleClass().add("anchor-pane");
		grid.getStylesheets().add("/ui/subpage/Timetable.css");

		return grid;
	}

	private void populateData (Timetable timetable, GridPane grid)
	{
		List<LessonPeriod> lessonPeriods = timetable.getLessonPeriods();

		/* Creates the first row with the LessonPeriods. */
		for (int i = 0; i < lessonPeriods.size(); i++)
		{
			LessonPeriod lessonPeriod = lessonPeriods.get(i);
			grid.add(new LessonPeriodView(i + 1, lessonPeriod), i + 1, 0);
		}

		List<String> days = timetable.getDays();

		for (int dI = 0; dI < days.size(); dI++)
		{
			String day = days.get(dI);
			Label dayLabel = new Label(day);
			dayLabel.getStyleClass().add("day-label");
			grid.add(dayLabel, 0, dI + 1);

			List<LessonSpot> lessonSpots = timetable.getLessonsForDay(day);

			for (int lI = 0; lI < lessonSpots.size(); lI++)
			{
				LessonSpot lessonSpot = lessonSpots.get(lI);

				if (lessonSpot == null)
					continue;

				LessonSpotView lessonView = new LessonSpotView(lessonSpot);
				grid.add(lessonView, lI + 1, dI + 1);
			}
		}
	}
}
