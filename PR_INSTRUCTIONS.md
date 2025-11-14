# How to create the PR and Release

## Create the PR (web)

Open this URL in your browser to start a PR for branch `feature/ui-reminders` against `main`:

https://github.com/DavidDully/TrackEd/pull/new/feature/ui-reminders

## Create the PR (CLI)

If you have the GitHub CLI installed (`gh`), you can create the PR from the repo root (PowerShell):

```powershell
gh auth login  # if not already authenticated
gh pr create --base main --head feature/ui-reminders --title "Add pale blue background + reminders UI" --body-file PR_BODY.md
```

## Create a release (CLI)

After the PR is merged, create a GitHub release for `v0.1.0` (PowerShell):

```powershell
gh release create v0.1.0 --title "v0.1.0" --notes-file RELEASE_NOTES.md
```

If you prefer, you can also open the Releases page in the browser and draft a release.
