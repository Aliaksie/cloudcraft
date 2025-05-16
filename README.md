# ☁️ CloudCraft

**CloudCraft** is an open-source toolkit for modeling, evaluating, and versioning cloud solution architectures — with support for multi-cloud patterns, architecture-as-code, and cloud migration planning.

## 🌟 Features

- 🔧 **Architecture-as-Code DSL** – YAML or fluent Java DSL to define your system
- 🧠 **Pattern Advisor** – Get suggestions like CQRS, Event Sourcing, or API Gateway
- ☁️ **Cloud Service Comparator** – Compare AWS, Azure, GCP service equivalents
- 🗺️ **Cloud Migration Advisor** – Plan your transition from on-prem to cloud
- 📊 **Diagram Generation** – Output to PlantUML, Mermaid, or Graphviz

## 🛣️ Roadmap

### ✅ MVP
- [ ] Core architecture model + validator
- [ ] DSL parser (YAML & Java)
- [ ] Pattern recommendation engine (basic rules)
- [ ] Cloud service mapping engine
- [ ] Mermaid/PlantUML diagram generation

### 🔜 Phase 1.x
- [ ] Cloud Migration Advisor (lift & shift, refactor, rearchitect)
- [ ] Plugin system for extensibility
- [ ] Terraform/Pulumi integration
- [ ] LLM-based natural language architecture assist (optional)

### 💡 Ideas for Later
- [ ] GitHub repo analyzer for app migration
- [ ] OpenAPI/dynamic analysis input support
- [ ] Architecture drift detection
- [ ] Collaborative editor (VSCode plugin or web UI)

## 🧱 Project Structure

| Module                        | Purpose                                                    |
|------------------------------|------------------------------------------------------------|
| `cloudcraft-core`            | Core model, validators, utilities, YAML/Java DSL parser... |
| `cloudcraft-cli`             | Command-line interface using Picocli                       |

## 🤝 Contributing

Contributions, ideas, and feedback are welcome!
