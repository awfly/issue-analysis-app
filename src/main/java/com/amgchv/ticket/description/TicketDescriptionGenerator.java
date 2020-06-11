package com.amgchv.ticket.description;

import com.amgchv.models.Issue;
import com.amgchv.models.Testcase;
import com.amgchv.models.User;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class TicketDescriptionGenerator {

    private static final String FROM = "От: ";
    private static final String HELLO_MESSAGE = "Добрый день, коллеги!";
    private static final String ERROR_MESSAGE = "В процессе проверки тесткейса - ";
    private static final String PART_OF = ", который является частью сценария - ";
    private static final String FACED_PROBLEM = ", мы столкнулись с проблемой.";
    private static final String TAKE_A_LOOK = "Не могли бы вы взглянуть?";
    private static final String BEFORE_TEST = "Перед началом тестеровния были проделаны следующие настройки: ";
    private static final String STEPS = "Тестирование проводилось по следующим шагам: ";
    private static final String START_TIME = "Тестирование было начато в: ";
    private static final String END_TIME = "Тестирование было окончено в: ";
    private static final String ER = "Ожидаемый результат: ";
    private static final String AR = "Действительный результат: ";
    private static final String SIMILAR_TICKETS_BY_EXCEPTION = "Найдено схожих тикетов по exception: ";
    private static final String SIMILAR_TICKETS_BY_KEYWORDS = "Найдено схожих тикетов по ключевым словам: ";

    public static String generateDescriptionFromTestcase(Testcase testcase, User user, LocalDateTime startDate, LocalDateTime endDate) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(FROM).append(user.getFirstName()).append(' ').append(user.getLastName()).append('\n');
        stringBuilder.append(HELLO_MESSAGE).append('\n');
        stringBuilder.append(ERROR_MESSAGE).append(testcase.getName()).append(PART_OF).append(testcase.getScenario().getName()).append(FACED_PROBLEM).append('\n');
        stringBuilder.append(TAKE_A_LOOK).append('\n');
        stringBuilder.append(START_TIME).append(startDate.truncatedTo(ChronoUnit.SECONDS)).append('\n');
        stringBuilder.append(END_TIME).append(endDate.truncatedTo(ChronoUnit.SECONDS)).append('\n');
        return stringBuilder.toString();
    }


    public static String addAdditionalField(String originalDescription, Issue issue) {
        StringBuilder stringBuilder = new StringBuilder(originalDescription);
        stringBuilder.append(BEFORE_TEST).append('\n').append(issue.getPrerequisites()).append('\n');
        stringBuilder.append(STEPS).append('\n').append(issue.getStepsToReproduce()).append('\n');
        stringBuilder.append(ER).append(issue.getExpectedResult()).append('\n');
        stringBuilder.append(AR).append(issue.getActualResult()).append('\n');

        if (!issue.getSimilarIssuesByException().isEmpty()) {
            stringBuilder.append(SIMILAR_TICKETS_BY_EXCEPTION).append(StringUtils.join(issue.getSimilarIssuesByException(), ", ")).append('\n');
        }
        if (!issue.getSimilarIssuesByKeywords().isEmpty()) {
            stringBuilder.append(SIMILAR_TICKETS_BY_KEYWORDS).append(StringUtils.join(issue.getSimilarIssuesByKeywords(), ", ")).append('\n');
        }

        return stringBuilder.toString();
    }
}
