# Retirement Decision: Duplicate Gradle CI Workflow

**Case:** PLC-VRL-001 — PetClinic Lifecycle Case

**Decision:** Retire `.github/workflows/gradle-build.yml` as an obsolete
delivery path. Maven Wrapper is the canonical publication and evidence path for
this companion. The application source and Gradle build files remain available
for reader translation; only the duplicate hosted workflow is retired.

**Authority:** Repository owner, bounded companion maintenance decision.

**Reason:** Two hosted build workflows created two apparent delivery authorities
while only the Maven path records the evidence bundle, SBOM digest, candidate
identity, and deterministic gate. Retiring the duplicate workflow reduces
ambiguity without changing application behavior.

**AI contribution allowed:** Search workflow references, identify duplicated
delivery paths, draft the retirement record, and propose residual checks.

**AI contribution not allowed:** Delete a workflow, alter build authority, or
claim that no consumer exists without the owner approving the retirement.

**Retained obligations:**

- Keep `build.gradle` and Gradle wrapper files for reader translation.
- Confirm no active workflow references the retired workflow filename.
- Preserve the Maven workflow as the only hosted delivery evidence path.
- Review the retirement again if the project adopts Gradle as its canonical
  build authority.

This is an educational workflow retirement, not evidence that a production
service, data set, credential, or infrastructure resource has been
decommissioned.
