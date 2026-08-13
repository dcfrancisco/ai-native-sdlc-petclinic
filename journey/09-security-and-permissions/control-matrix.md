# Reminder Contribution Control Matrix

| Resource | Allowed action | Denied action | Enforcement | Approval | Evidence |
| --- | --- | --- | --- | --- | --- |
| Reminder source and tests | Read and modify approved files | Modify unrelated packages | Scoped workspace plus diff review | Human owner for scope expansion | Modified-file inventory |
| Repository history | Inspect status and diff | Push, merge, rewrite history | Repository credentials and branch protection | Maintainer | Git audit record |
| Secrets | Reference variable names | Read, print, store, or commit values | Secret isolation and redaction | Security owner | Secret scan and execution log |
| Network | Resolve approved build dependencies | Contact messaging providers | Sandbox or firewall policy | Architecture and security owners | Network policy and denied-action record |
| Docker | Build and run local image | Push image or access production daemon | Registry credentials and runtime policy | Release owner | Command log and registry audit |
| Database | Use local H2 or companion containers | Access shared or production databases | Connection configuration and credentials | Data owner | Connection target and test evidence |
| CI | Read published results | Change protected workflow or secrets | Repository permissions | Maintainer and security owner | Workflow history |
| Deployment | Prepare release evidence | Deploy or approve release | Deployment RBAC and approval gate | Release owner | Approval and deployment audit |

## Implemented least-privilege example

The Maven workflow declares:

```yaml
permissions:
  contents: read
```

This is an enforceable platform permission, not a prose instruction. The workflow can check out and inspect repository content, but its automatically issued token is not granted repository write authority. A contribution that needs to push, merge, create a release, or alter repository settings must stop and obtain a separately approved identity and workflow.

The declaration does not prove that every external action is impossible. A self-hosted runner, separately supplied credential, or overly broad organizational integration could add authority through another channel. Review the complete execution environment before relying on this control.

## Review note

Diff review is detective, not preventive. High-risk contributions should use a workspace or permission boundary that prevents unrelated writes rather than relying only on later review.
