package org.fossasia.openevent.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import org.fossasia.openevent.data.Speaker;
import org.fossasia.openevent.dbutils.LiveRealmData;
import org.fossasia.openevent.dbutils.RealmDataRepository;

import java.util.List;

import static org.fossasia.openevent.utils.SortOrder.sortOrderSpeaker;

public class SpeakersListFragmentViewModel extends ViewModel {

    private LiveData<List<Speaker>> speakersList;
    private RealmDataRepository realmRepo;
    private String searchText = "";
    private int speakersListSortType = 0;

    public SpeakersListFragmentViewModel() {
        realmRepo = RealmDataRepository.getDefaultInstance();
        speakersList = new MutableLiveData<>();
        subscribeToSpeakers();
    }

    private void subscribeToSpeakers() {
        LiveRealmData<Speaker> speakerLiveRealmData = RealmDataRepository.asLiveData(realmRepo.getSpeakers(sortOrderSpeaker()));
        speakersList = Transformations.map(speakerLiveRealmData, input -> input);
    }

    public LiveData<List<Speaker>> getSpeakers(int sortType) {
        if (sortType != speakersListSortType) {
            subscribeToSpeakers();
            speakersListSortType = sortType;
        }
        return speakersList;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
