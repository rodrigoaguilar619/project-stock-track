CREATE TRIGGER trigger_issues_manager_track_fair_value
AFTER INSERT
ON issues_historical_fair_value
FOR EACH ROW
BEGIN
    UPDATE issues_manager_track_properties 
    SET track_fair_value = NEW.fair_value
    WHERE id_issue = NEW.id_issue;
END