package org.yearup.data;


import org.yearup.models.Profile;

public interface ProfileDao
{
    Profile create(Profile profile);

    Profile getById(int id);

    void deleteProfile(int id);

    void updateProfile(int id, Profile profile);
}
