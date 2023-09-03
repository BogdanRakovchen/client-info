package ru.client.clientinfo.interfaces;

import ru.client.clientinfo.entity.ContactsEntity;

public interface IContacts {

    void addContact(ContactsEntity contactsEntity, String id);
}
