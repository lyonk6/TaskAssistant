package api.v1.helper;

import api.v1.error.BusinessException;
import api.v1.error.SystemException;
import api.v1.model.*;
import api.v1.repo.*;
import java.util.ArrayList;

/**
 * Created by kennethlyon on 12/27/16.
 */
public class RepositoryHelper {
    /**
     * Fetch an ArrayList of Categories from the provided repository. Note that these
     * Categories are deep copies and changes to these will not directly affect the
     * current model.
     *
     * @param categoryRepository
     * @param categoryIds
     * @return
     * @throws BusinessException
     * @throws SystemException
     */
    protected ArrayList<Category> fetchCategories(CategoryRepository categoryRepository, ArrayList<Integer> categoryIds) throws BusinessException, SystemException {
        ArrayList<Category> myCategories = new ArrayList<Category>();
        if(categoryIds==null)
            return myCategories;
        for(int i: categoryIds) {
            Category category=new Category();
            category.setId(i);
            category=categoryRepository.get(category);
            myCategories.add(category);
        }
        return myCategories;
    }


    /**
     * Fetch an ArrayList of Tasks from the provided repository. Note that these
     * Tasks are deep copies and changes to these will not directly affect the
     * current model.
     *
     * @param taskRepository
     * @param taskIds
     * @return
     * @throws BusinessException
     * @throws SystemException
     */
    protected ArrayList<Task> fetchTasks(TaskRepository taskRepository, ArrayList<Integer> taskIds) throws BusinessException, SystemException {
        ArrayList<Task> myTasks = new ArrayList<Task>();
        if(taskIds==null)
            return myTasks;
        for(int i: taskIds) {
            Task task=new Task();
            task.setId(i);
            task=taskRepository.get(task);
            myTasks.add(task);
        }
        return myTasks;
    }

    /**
     * Fetch an ArrayList of TaskLists from the provided repository. Note that these
     * TaskLists are deep copies and changes to these will not directly affect the
     * current model.
     *
     * @param taskListRepository
     * @param taskListIds
     * @return
     * @throws BusinessException
     * @throws SystemException
     */
    protected ArrayList<TaskList> fetchTaskLists(TaskListRepository taskListRepository, ArrayList<Integer> taskListIds) throws BusinessException, SystemException {
        ArrayList<TaskList> myTaskLists = new ArrayList<TaskList>();
        if(taskListIds==null)
            return myTaskLists;
        for(int i: taskListIds) {
            TaskList taskList=new TaskList();
            taskList.setId(i);
            taskList=taskListRepository.get(taskList);
            myTaskLists.add(taskList);
        }
        return myTaskLists;
    }

    /**
     * Fetch an ArrayList of Schedules from the provided repository. Note that these
     * Schedules are deep copies and changes to these will not directly affect the
     * current model.
     *
     * @param scheduleRepository
     * @param scheduleIds
     * @return
     * @throws BusinessException
     * @throws SystemException
     */
    protected ArrayList<Schedule> fetchSchedules(ScheduleRepository scheduleRepository, ArrayList<Integer> scheduleIds) throws BusinessException, SystemException {
        ArrayList<Schedule> mySchedules = new ArrayList<Schedule>();
        if(scheduleIds==null)
            return mySchedules;
        for(int i: scheduleIds) {
            Schedule schedule=new Schedule();
            schedule.setId(i);
            schedule=scheduleRepository.get(schedule);
            mySchedules.add(schedule);
        }
        return mySchedules;
    }

    /**
     * Fetch an ArrayList of Reminders from the provided repository. Note that these
     * Reminders are deep copies and changes to these will not directly affect the
     * current model.
     *
     * @param reminderRepository
     * @param reminderIds
     * @return
     * @throws BusinessException
     * @throws SystemException
     */
    protected ArrayList<Reminder> fetchReminders(ReminderRepository reminderRepository, ArrayList<Integer> reminderIds) throws BusinessException, SystemException {
        ArrayList<Reminder> myReminders = new ArrayList<Reminder>();
        if(reminderIds==null)
            return myReminders;
        for(int i: reminderIds) {
            Reminder reminder=new Reminder();
            reminder.setId(i);
            reminder=reminderRepository.get(reminder);
            myReminders.add(reminder);
        }
        return myReminders;
    }

    /**
     * Fetch an ArrayList of Calendars from the provided repository. Note that these
     * Calendars are deep copies and changes to these will not directly affect the
     * current model.
     *
     * @param calendarRepository
     * @param calendarIds
     * @return
     * @throws BusinessException
     * @throws SystemException
     */
    protected ArrayList<Calendar> fetchCalendars(CalendarRepository calendarRepository, ArrayList<Integer> calendarIds) throws BusinessException, SystemException {
        ArrayList<Calendar> myCalendars = new ArrayList<Calendar>();
        if(calendarIds==null)
            return myCalendars;
        for(int i: calendarIds) {
            Calendar calendar=new Calendar();
            calendar.setId(i);
            calendar=calendarRepository.get(calendar);
            myCalendars.add(calendar);
        }
        return myCalendars;
    }

    /**
     * Fetch an ArrayList of TimeBlocks from the provided repository. Note that these
     * TimeBlocks are deep copies and changes to these will not directly affect the
     * current model.
     *
     * @param timeBlockRepository
     * @param timeBlockIds
     * @return
     * @throws BusinessException
     * @throws SystemException
     *
    protected ArrayList<TimeBlock> fetchTimeBlocks(TimeBlockRepository timeBlockRepository, ArrayList<Integer> timeBlockIds) throws BusinessException, SystemException {
        ArrayList<TimeBlock> myTimeBlocks = new ArrayList<TimeBlock>();
        if(timeBlockIds==null)
            return myTimeBlocks;
        for(int i: timeBlockIds) {
            TimeBlock timeBlock=new TimeBlock();
            timeBlock.setId(i);
            timeBlock=timeBlockRepository.get(timeBlock);
            myTimeBlocks.add(timeBlock);
        }
        return myTimeBlocks;
    }//*/

    /**
     * Fetch an ArrayList of Users from the provided repository. Note that these
     * Users are deep copies and changes to these will not directly affect the
     * current model.
     *
     * @param userRepository
     * @param userIds
     * @return
     * @throws BusinessException
     * @throws SystemException
     */
    protected ArrayList<User> fetchUsers(UserRepository userRepository, ArrayList<Integer> userIds) throws BusinessException, SystemException {
        ArrayList<User> myUsers = new ArrayList<User>();
        if(userIds==null)
            return myUsers;
        for(int i: userIds) {
            User user=new User();
            user.setId(i);
            user=userRepository.get(user);
            myUsers.add(user);
        }
        return myUsers;
    }


}
