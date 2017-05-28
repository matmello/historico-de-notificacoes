package br.com.firstsoft.historicodenotificacoes.dao;

import java.util.List;

import br.com.firstsoft.historicodenotificacoes.model.CNotification;

public interface CNotificationDao {
    public CNotification fetchNotificationById(int nofiticationId);
    public List<CNotification> fetchAllNotifications();
    public boolean addNotification(CNotification notification);
    public boolean addNotifications(List<CNotification> notifications);
    public boolean deleteAllNotifications();
    public boolean deleteUser(CNotification user);
}