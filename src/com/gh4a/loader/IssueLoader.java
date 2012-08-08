package com.gh4a.loader;

import java.io.IOException;

import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.IssueService;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.gh4a.Constants;
import com.gh4a.DefaultClient;
import com.gh4a.Gh4Application;

public class IssueLoader extends AsyncTaskLoader<Issue> {

    private String mRepoOwner;
    private String mRepoName;
    private int mIssueNumber;
    
    public IssueLoader(Context context, String repoOwner, String repoName, int issueNumber) {
        super(context);
        mRepoOwner = repoOwner;
        mRepoName = repoName;
        mIssueNumber = issueNumber;
    }

    @Override
    public Issue loadInBackground() {
        Gh4Application app = (Gh4Application) getContext().getApplicationContext();
        GitHubClient client = new DefaultClient();
        client.setOAuth2Token(app.getAuthToken());
        IssueService issueService = new IssueService(client);
        try {
            return issueService.getIssue(mRepoOwner, mRepoName, mIssueNumber);
        } catch (IOException e) {
            Log.e(Constants.LOG_TAG, e.getMessage(), e);
            return null;
        }
    }

}
