# Multi Layout Video

This repository currently contains a Codex skill for generating Jetpack Compose UI for an Android multi-video editor app.

## Skill

The skill lives at `multi-video-editor-compose-ui/` and includes:

- `SKILL.md`: the main skill instructions
- `agents/openai.yaml`: skill metadata
- `references/screens.md`: extra screen-specific guidance

## Intended app scope

The skill is tailored for an Android app that can:

- pick 2 to 4 videos
- preview layouts such as side-by-side, top/bottom, PiP, and grid
- adjust trim, offset, mute, and volume
- export a combined MP4

## Usage

Use the `multi-video-editor-compose-ui` skill when generating or refining Jetpack Compose screens for:

- home
- video picker entry
- editor
- clip settings bottom sheet
- export sheet
- export progress
- export success

The skill is UI-only by default. It should not introduce ViewModels, repositories, navigation graphs, or media/export implementation unless explicitly requested.
