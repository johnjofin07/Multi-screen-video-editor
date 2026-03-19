---
name: multi-video-editor-compose-ui
description: generate polished jetpack compose ui for this specific android multi-video editor app. use when creating or improving compose screens for the app's home, video picker entry, editor, clip settings bottom sheet, export sheet, export progress, or export success screens. apply capcut/inshot-like black and white styling, rounded cards and buttons, simple timeline patterns, preview-first layout, and low-clutter mobile structure. output complete compose screens with preview composables and fake sample data.
---

Generate UI only for this repository. Do not add ViewModels, repositories, navigation graphs, networking, business logic, or export/media implementation unless the user explicitly asks.

## Repo-only scope
- Use this skill only when working inside this repository for the Android multi-video editor app.
- Treat the repository's existing package structure, theming, naming, and component patterns as the source of truth when they exist.
- Do not generalize output for unrelated apps, sample apps, or generic creator tools.
- Do not introduce screens, features, or flows that are outside this repository's app plan unless explicitly requested.

## App context
This skill is only for one app: an Android multi-video editor that lets users:
- pick 2 to 4 videos from the standard gallery picker
- preview videos in layouts like side-by-side, top/bottom, pip, and 2x2 grid
- do basic sync actions such as trim, offset, mute, and volume
- export a single MP4 with layout preserved

## Output contract
For every requested screen, return:
1. one complete Jetpack Compose screen in a single Kotlin file
2. one or more `@Preview` composables
3. fake sample data needed by the previews

Unless the user asks otherwise, include all helper composables needed by that screen in the same file.

## Compose-only output rules
- Generate only Jetpack Compose UI code.
- Return complete screen composables, local child composables, previews, and preview-only sample models/data.
- Do not generate ViewModels, repositories, use cases, navigation graphs, API clients, Room code, media player controllers, export pipelines, or other non-UI architecture unless explicitly requested.
- Keep output focused on layout, styling, interaction surfaces, and UI-state parameters.

## UI stack
- Use Jetpack Compose
- Use Material 3 for standard primitives like scaffold, app bars, cards, buttons, sheets, dialogs, chips, indicators, and text fields
- Use custom Compose layouts for the editor canvas, timeline strip, clip rows, layout previews, and any video-editing specific UI

## Design direction
Use a CapCut/InShot-like feel with these constraints:
- black and white dominant color system
- high contrast
- rounded cards and rounded buttons
- preview-first hierarchy
- simple timeline, never dense or overly technical
- no clutter; show only the most important controls up front
- creator-friendly, not enterprise or pro-studio looking

Translate that style into a clean Android UI rather than copying brand assets or exact screens.

## Color and style rules
- Prefer black, white, and neutral grays
- Use a single subtle accent only when needed for status or emphasis
- Keep backgrounds mostly black, white, or near-black depending on contrast needs within the screen
- Use generous spacing and rounded corners
- Avoid heavy outlines, nested containers, and too many dividers
- Avoid colorful gradients unless explicitly requested
- Avoid overly dark pro-editor chrome with many stacked toolbars

## Layout rules
- The main preview area should dominate editor-related screens
- Put primary actions where thumbs can reach them easily
- Use bottom sheets or bottom tool areas for clip actions instead of side panels
- Group related controls into small rounded sections
- Keep timeline controls simple and readable
- Prefer horizontal chips/cards for layout selection
- On smaller screens, collapse secondary actions behind a sheet or overflow

## Screen-specific guidance

### Home
Include:
- app title or simple brand area
- primary CTA to start/select videos
- recent projects section if relevant
- clean empty state when there are no projects

### Video Picker Entry
Include:
- short explanation that user can choose 2 to 4 videos
- obvious CTA to open the system picker
- small note about supported layouts or export goal

### Editor
Include:
- large preview section at the top
- play/pause and scrub controls
- layout selector chips or cards
- simple timeline row or clip strip area
- fast access to clip settings
- export action clearly visible

### Clip Settings Bottom Sheet
Include:
- file/clip label
- trim controls
- offset controls
- mute toggle
- volume control
- simple, touch-friendly spacing

### Export Sheet
Include:
- filename field
- export preset chooser
- audio mode summary or selector
- final export CTA

### Export Progress
Include:
- strong progress feedback
- cancel action if present
- simple summary of current export settings
- minimal distractions

### Export Success
Include:
- success confirmation
- thumbnail or result summary if useful
- open, share, and done actions

## Compose code rules
- Use Material 3 APIs where appropriate
- Keep composables small and readable
- Extract repeated UI into local private composables in the same file
- Use sensible parameter names
- Prefer immutable sample data models in the same file when needed for previews
- Keep fake sample data in clearly labeled preview-only objects or helpers so it can be replaced later without changing the screen layout
- Add `Modifier` parameters for reusable child composables when reasonable
- Keep code production-lean; avoid placeholder comments like "TODO design this better"

## State handling rules
Because this skill is UI-only:
- accept UI state as function parameters when needed
- use fake local sample state for previews
- keep fake state isolated and easy to replace later with real repository/ViewModel/domain state
- do not create ViewModels unless explicitly requested
- do not wire real media playback or picker launch logic unless explicitly requested

## Replaceable fake data rules
- Fake sample data exists only to power previews and initial screen scaffolding.
- Put fake data in clearly named helpers such as `PreviewData`, `fakeEditorState()`, or `sampleClipItems()`.
- Keep fake models/data near the bottom of the file or in a clearly marked preview section.
- Avoid coupling fake data to production logic, DI, storage, or networking.
- Structure the composables so real state can replace fake data later by swapping parameters, not rewriting the layout.

## Accessibility and mobile quality bar
- Respect touch target sizing
- Use clear text hierarchy
- Add content descriptions for key icon-only actions
- Ensure buttons and chips are legible
- Avoid tiny timeline handles or dense rows
- Prefer fewer actions per row over cramped layouts

## Default file pattern
Use this pattern unless the user requests another format:

```kotlin
@Composable
fun ScreenName(...)

@Preview
@Composable
private fun ScreenNamePreview()
```

Include any small fake models and preview data in the same file.

## What to avoid
- XML layouts
- Material 2 components unless required
- overbuilt architecture
- crowded control bars
- desktop-style editing UI
- bright multi-color creator UI
- tiny timeline controls
- multiple competing CTAs on one screen

## Response style
When asked to generate a screen:
- provide the Kotlin Compose file directly
- optimize for this app's known flow and constraints
- make pragmatic UI decisions without asking unnecessary clarifying questions
- if a requested screen conflicts with the skill rules, adapt it to the closest mobile-friendly version
